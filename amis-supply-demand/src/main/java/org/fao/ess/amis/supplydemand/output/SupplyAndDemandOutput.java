package org.fao.ess.amis.supplydemand.output;

import org.fao.fenix.export.core.dto.CoreOutputHeader;
import org.fao.fenix.export.core.dto.data.CoreData;
import org.fao.fenix.export.core.output.plugin.Output;

import java.io.OutputStream;
import java.util.Map;

public class SupplyAndDemandOutput extends Output{
    @Override
    public void init(Map<String, Object> map) {

    }

    @Override
    public void process(CoreData coreData) throws Exception {

    }

    @Override
    public CoreOutputHeader getHeader() throws Exception {
        return null;
    }

    @Override
    public void write(OutputStream outputStream) throws Exception {

    }
}
