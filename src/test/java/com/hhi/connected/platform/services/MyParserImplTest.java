package com.hhi.connected.platform.services;

import com.hhi.connected.platform.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Configuration
@ComponentScan(
        basePackages="com.hhi.connected.platform",
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, value = Configuration.class)
)
public class MyParserImplTest {

    @Autowired
    MyParser myParser;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void parse() throws Exception {
        String message = "{\"Mechanical\":{\"Machinery\":{\"DieselEngine\":{\"GeneratorEngine4\":{\"Cylinder7\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"285.1501770019531\",0]}}},\"Cylinder4\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"283.1695251464844\",0]}}},\"HTCFWOutletSensor1\":{\"Temperature\":{\"val\":[1459145504589,\"70.57168579101562\",0]}},\"HTCFWInletSensor1\":{\"Pressure\":{\"val\":[1459145504589,\"3.2935450077056885\",0]}},\"Cylinder5\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"284.7137145996094\",0]}}},\"Cylinder1\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"291.7969665527344\",0]}}},\"TurboCharger1\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"318.6192932128906\",0]}},\"ExhGasInlet1\":{\"Temperature\":{\"val\":[1459145504589,\"440.8135070800781\",0]}}},\"FOSystemSensor1\":{\"Temperature\":{\"val\":[1459145504589,\"102.16091918945312\",0]},\"Pressure\":{\"val\":[1459145504589,\"6.198764801025391\",0]}},\"LTCFWInletSensor1\":{\"Pressure\":{\"val\":[1459145504589,\"2.5058140754699707\",0]}},\"Cylinder2\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"281.1889343261719\",0]}}},\"Cylinder6\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"276.1534118652344\",0]}}},\"Cylinder3\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"295.3218078613281\",0]}}},\"Cylinder8\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"286.8957824707031\",0]}}},\"LOSystemSensor1\":{\"Temperature\":{\"val\":[1459145504589,\"57.41229248046875\",0]},\"Pressure\":{\"val\":[1459145504589,\"6.220303058624268\",0]}}},\"GeneratorEngine3\":{\"LTCFWInletSensor1\":{\"Pressure\":{\"val\":[1459145504589,\"2.3627140522003174\",0]}},\"Cylinder5\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"61.37353515625\",0]}}},\"TurboCharger1\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"33.04058837890625\",0]}},\"ExhGasInlet1\":{\"Temperature\":{\"val\":[1459145504589,\"42.305877685546875\",0]}}},\"LOSystemSensor1\":{\"Pressure\":{\"val\":[1459145504589,\"2.326504945755005\",0]},\"Temperature\":{\"val\":[1459145504589,\"45.897857666015625\",0]}},\"Cylinder1\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"61.4071044921875\",0]}}},\"Cylinder4\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"61.4071044921875\",0]}}},\"FOSystemSensor1\":{\"Temperature\":{\"val\":[1459145504589,\"102.59732055664062\",0]},\"Pressure\":{\"val\":[1459145504589,\"6.131381988525391\",0]}},\"HTCFWOutletSensor1\":{\"Temperature\":{\"val\":[1459145504589,\"68.79248046875\",0]}},\"HTCFWInletSensor1\":{\"Pressure\":{\"val\":[1459145504589,\"2.416274070739746\",0]}},\"GESystemSensor1\":{\"RPM\":{\"Response\":{\"val\":[1459145504589,\"0.0\",0]}}},\"Cylinder2\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"61.977813720703125\",0]}}},\"Cylinder6\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"60.2657470703125\",0]}}},\"Cylinder3\":{\"ExhGasOutlet1\":{\"Temperature\":{\"val\":[1459145504589,\"62.347076416015625\",0]}}}}}}},\"Electrical\":{\"ElectricRotatingMachine\":{\"Generator4\":{\"SystemSensor1\":{\"Running\":{\"stVal\":[1459145504589,\"true\",0]}}},\"Generator3\":{\"SystemSensor1\":{\"Running\":{\"stVal\":[1459145504589,\"false\",0]}}}}}}";
        assertNotNull(myParser.parse(message));
        assertNull(myParser.parse(""));
        assertNull(myParser.parse(null));

    }
}