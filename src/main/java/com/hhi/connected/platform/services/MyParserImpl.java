package com.hhi.connected.platform.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.connected.platform.handlers.ModelHandler;
import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.models.enums.ModelType;
import com.hhi.connected.platform.services.utils.MyListUtils;
import com.hhi.connected.platform.services.utils.ShipTopologyModule;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MyParserImpl implements MyParser{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyParserImpl.class);

    private static final String VALUE = "value";
    private static final String VALID = "valid";

    private final static String data = "vdmSampleContent";
    private final static String alarm = "alarmSampleContent";
    private final static String config = "shipTopology";

    @Autowired
    private ShipTopologyModule shipTopologyModule;

    @Autowired
    private ModelHandler modelHandler;

    @Autowired
    private CacheDataService cacheDataService;

    public void setCacheDataService(CacheDataService cacheDataService) {
        this.cacheDataService = cacheDataService;
    }
    public CacheDataService getCacheDataService() {
        return cacheDataService;
    }

    public void setShipTopologyModule(ShipTopologyModule shipTopologyModule) {
        this.shipTopologyModule = shipTopologyModule;
    }

    public void setModelHandler(ModelHandler modelHandler) {
        this.modelHandler = modelHandler;
    }

    @Override
    public String parse(String message) {
        try {
            if(StringUtils.isEmpty(message)){
                return null;
            }
            ModelType type = getModelType(message);
            if(type == null){
                return null;
            }

            return toJson(shipTopologyModule.refine(new ObjectMapper().readValue(message, new TypeReference<Map<String, Object>>() {})), type);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }

    }

    private String toJson(Map<String, Object> message, ModelType type) {
        try {


            Map<String, List<BaseModel>> sorted = modelHandler.process(message, type).stream().collect(Collectors.groupingBy(BaseModel::getKey));

            if(MapUtils.isEmpty(sorted))
            {
                LOGGER.debug("sorted map is null");
                return null;
            }

            // TODO need to bulk process for only getting data from the cache but also updating latest data to the cache
            List result = sorted.entrySet().stream().map(this::removeSequentialDuplicates).filter(e -> !e.getValue().isEmpty()).map(convertListToSingleStringFunction()).collect(Collectors.toList());

            return CollectionUtils.isEmpty(result) ? null : new ObjectMapper().writeValueAsString(result);

        } catch (JsonProcessingException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    private ModelType getModelType(Map<String, Object> payload) {

        // TODO do this with enum value(int -> String)
        if(payload.get(data) != null){
            return ModelType.DATA;
        } else if(payload.get(alarm) != null){
            return ModelType.ALARM;
        } else if(payload.get(config) != null){
            return ModelType.CONFIG;
        }
        return null;
    }

    private ModelType getModelType(String payload) {

        // TODO do this with enum value(int -> String)
        if(payload.contains(data)){
            return ModelType.DATA;
        } else if(payload.contains(alarm)){
            return ModelType.ALARM;
        } else if(payload.contains(config)){
            return ModelType.CONFIG;
        }
        return null;
    }

    public Map.Entry<String, List<BaseModel>> removeSequentialDuplicates(Map.Entry<String, List<BaseModel>> entry) {

        List tobeRemoved = new ArrayList<>();

        entry = loadFromCache(entry, tobeRemoved);

        // FIXME || MODIFY ME
        for(int i = 0; i < entry.getValue().size(); i++){
            if(i > 0  && isSequentiallyDuplicated(entry.getValue(), i)){
                tobeRemoved.add(entry.getValue().get(i));
            }
        }

        if(CollectionUtils.isNotEmpty(tobeRemoved)){
            entry.setValue(MyListUtils.subtract().apply(new ArrayList<>(entry.getValue())).apply(tobeRemoved));
        }

        cacheDataService.update(entry);

        return entry;
    }

    private Map.Entry<String, List<BaseModel>> loadFromCache(Map.Entry<String, List<BaseModel>> entry, List tobeRemoved) {
        BaseModel cached = cacheDataService.get(entry.getKey());

        if(cached != null) {
            entry.setValue(MyListUtils.addFirst().apply(new ArrayList<>(entry.getValue())).apply(cached));
            tobeRemoved.add(cached);
        }

        return entry;
    }

    private boolean isSequentiallyDuplicated(List<BaseModel> e, int i) {
        return e.get(i).getValues().get(VALUE).equals(e.get(i - 1).getValues().get(VALUE));
    }

    private Function<Map.Entry<String, List<BaseModel>>, Map> convertListToSingleStringFunction() {
        return e -> Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(
                        e.getKey(),
                        e.getValue()
                                .stream().map(m -> Arrays.asList(m.getTimestamp(), m.getValues().get(VALUE), m.getValues().get(VALID))).flatMap(Collection::stream).collect(Collectors.toList()))
        ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

}
