package org.fao.gift.input;

import org.fao.fenix.commons.msd.dto.data.Resource;
import org.fao.fenix.export.core.dto.data.CoreData;
import org.fao.fenix.export.core.input.plugin.Input;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@org.fao.fenix.commons.utils.annotations.export.Input("d3sInputPluginStandard")
public class GiftSurveyInput  extends Input {


    @Override
    public void init(Map<String, Object> config, Resource resource) {

    }

    @Override
    public CoreData getResource() {

        return null;
    }


   /* //UTILS
    private boolean existsTemplate(Properties properties) throws IOException {
        return this.config != null && this.config.containsKey(TEMPLATE) && this.config.get(TEMPLATE) != null && this.config.get(TEMPLATE) != ""
                && properties.containsKey(this.config.get(TEMPLATE));
    }*/



}
