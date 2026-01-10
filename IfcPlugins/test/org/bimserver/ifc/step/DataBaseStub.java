package org.bimserver.ifc.step;

import org.bimserver.BimserverDatabaseException;
import org.bimserver.plugins.deserializers.DatabaseInterface;
import org.bimserver.shared.VirtualObject;
import org.eclipse.emf.ecore.EClass;

import java.util.*;

class DataBaseStub implements DatabaseInterface {
    private long oid = 1;
    protected final Map<Long, EClass> classMap = new HashMap<>();

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
