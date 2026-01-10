package org.bimserver.ifc.step;

import org.apache.commons.io.IOUtils;
import org.bimserver.BimserverDatabaseException;
import org.bimserver.emf.PackageMetaData;
import org.bimserver.emf.Schema;
import org.bimserver.ifc.step.deserializer.Ifc4StepStreamingDeserializer;
import org.bimserver.models.ifc4.Ifc4Package;
import org.bimserver.plugins.deserializers.DatabaseInterface;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.shared.QueryContext;
import org.bimserver.shared.VirtualObject;
import org.eclipse.emf.ecore.EClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Ifc4StepStreamingDeserializerTests {
    final static String location = "https://github.com/opensourceBIM/TestFiles/raw/master/TestData/data/ifc4add2tc1/";

    @Test
    public void testOfficialFiles() throws IOException, DeserializeException {
        Map<String, Integer> testFiles = Map.of(
                "basin-advanced-brep.ifc", 152,
                "slab-standard-case.ifc", 41,
                "tessellation-with-individual-colors.ifc", 29
        ); // TODO create expectation class and objects
        for(Map.Entry<String, Integer> testFile: testFiles.entrySet()){
            String testFileName = testFile.getKey();
            int testFileSize = testFile.getValue();
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
    
    static class DataBaseStub implements DatabaseInterface {
        private long oid = 1;
        private final Map<Long, EClass> classMap = new HashMap<>();

        @Override
        public EClass getEClassForOid(long l) throws BimserverDatabaseException {
            return classMap.get(l);
        }

        @Override
        public short getCidOfEClass(EClass eClass) {
            return 0;
        }

        @Override
        public long newOid(EClass eClass) {
            classMap.put(oid, eClass);
            return oid++;
        }

        @Override
        public int save(VirtualObject virtualObject) throws BimserverDatabaseException {
            // TODO track for assertions
            return 0;
        }

        @Override
        public int saveOverwrite(VirtualObject virtualObject) throws BimserverDatabaseException {
            // TODO track for assertions
            return 0;
        }

        @Override
        public byte[] get(String s, byte[] bytes) throws BimserverDatabaseException {
            return new byte[0];
        }

        @Override
        public List<byte[]> getDuplicates(String s, byte[] bytes) throws BimserverDatabaseException {
            return Collections.emptyList();
        }

        @Override
        public UUID newUuid() {
            return UUID.randomUUID();
        }
    }
}
