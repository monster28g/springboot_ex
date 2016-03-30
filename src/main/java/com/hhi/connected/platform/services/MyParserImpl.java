package com.hhi.connected.platform.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhi.connected.platform.handlers.ModelHandler;
import com.hhi.connected.platform.models.BaseModel;
import com.hhi.connected.platform.services.utils.ShipTopologyModule;
import org.apache.commons.collections4.CollectionUtils;
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

    @Autowired
    private ShipTopologyModule shipTopologyModule;

    @Autowired
    private ModelHandler modelHandler;

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
            return toJson(shipTopologyModule.refine(
                    new ObjectMapper().readValue(message, new TypeReference<Map<String, Object>>() {})
            ));
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }

    }

    private String toJson(Map<String, Object> message) {
        try {
            Map<String, List<BaseModel>> sorted = modelHandler.process(message).stream().collect(Collectors.groupingBy(BaseModel::getKey));
            return new ObjectMapper().writeValueAsString(sorted.entrySet().stream().map(this::removeSequentialDuplicates).map(convertListToSingleStringFunction()).collect(Collectors.toList()));

        } catch (JsonProcessingException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }

    // FIXME
    public Map.Entry<String, List<BaseModel>> removeSequentialDuplicates(Map.Entry<String, List<BaseModel>> e) {

        Collection<BaseModel> tobeRemoved = new ArrayList<>();

        for(int i = 0; i < e.getValue().size(); i++){
            if(i > 0  && isSequentiallyDuplicated(e.getValue(), i)){
                tobeRemoved.add(e.getValue().get(i));
            }
        }

        if(CollectionUtils.isNotEmpty(tobeRemoved)){
            List<BaseModel> tmp = new ArrayList<>(e.getValue());
            tmp.removeAll(tobeRemoved);
            e.setValue(tmp);
        }

        return e;
    }

    private boolean isSequentiallyDuplicated(List<BaseModel> e, int i) {
        return e.get(i).getValue().equals(e.get(i - 1).getValue());
    }

    private Function<Map.Entry<String, List<BaseModel>>, Map> convertListToSingleStringFunction() {
        return e -> Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(
                        e.getKey(),
                        e.getValue()
                                .stream().map(m -> Arrays.asList(m.getTimestamp(), m.getValue(), m.getValid())).flatMap(Collection::stream)
                                .collect(Collectors.toList()))
        ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

}
