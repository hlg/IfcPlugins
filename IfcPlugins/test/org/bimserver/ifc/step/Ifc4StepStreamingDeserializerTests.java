package org.bimserver.ifc.step;

import org.apache.commons.io.IOUtils;
import org.bimserver.emf.PackageMetaData;
import org.bimserver.emf.Schema;
import org.bimserver.ifc.step.deserializer.Ifc4StepStreamingDeserializer;
import org.bimserver.models.ifc4.Ifc4Package;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.shared.QueryContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@RunWith(Parameterized.class)
public class Ifc4StepStreamingDeserializerTests {
    final static String location = "https://github.com/opensourceBIM/TestFiles/raw/master/TestData/data/ifc4add2tc1/";

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"basin-advanced-brep.ifc", 152},
                {"slab-standard-case.ifc", 41},
                {"tessellation-with-individual-colors.ifc", 29}
        });

    }
    @Parameter
    public String testFileName;

    @Parameter(1)
    public int testFileSize;

    // TODO create more expectation parameters

    @Test
    public void testOfficialFiles() throws IOException, DeserializeException {
        InputStream openStream = new URL(location + testFileName).openStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(openStream, baos);
        Ifc4StepStreamingDeserializer deserializer = new Ifc4StepStreamingDeserializer();
        PackageMetaData packageMetaData = new PackageMetaData(Ifc4Package.eINSTANCE, Schema.IFC4, Paths.get("tmp"));
        deserializer.init(packageMetaData);
        DataBaseStub databaseStub = new DataBaseStub();
        deserializer.read(new ByteArrayInputStream(baos.toByteArray()), testFileName, -1, new QueryContext(databaseStub, packageMetaData, -1, -1, -1, -1, -1));
        Assert.assertEquals(testFileSize, databaseStub.classMap.size());
    }

}
