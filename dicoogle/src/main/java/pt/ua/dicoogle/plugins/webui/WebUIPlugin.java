/**
 * Copyright (C) 2014  Universidade de Aveiro, DETI/IEETA, Bioinformatics Group - http://bioinformatics.ua.pt/
 *
 * This file is part of Dicoogle/dicoogle.
 *
 * Dicoogle/dicoogle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dicoogle/dicoogle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dicoogle.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ua.dicoogle.plugins.webui;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;

/** A POJO data type containing the full description of a Web UI plugin.
 *
 * @author Eduardo Pinho <eduardopinho@ua.pt>
 */
public class WebUIPlugin implements Cloneable {
    private String name;
    private String description;
    private String version;
    private String slotId;
    private String moduleFile;
    private JSONObject settings;
    private boolean enabled = true;

    public WebUIPlugin() {}

    @Override
    protected WebUIPlugin clone() throws CloneNotSupportedException {
        return (WebUIPlugin)super.clone();
    }

    /** Make a copy of the descriptor.
     * @return a copy of this descriptor
     * @todo The settings are shallowly copied at the moment. Hopefully this will not be a problem, but
     * bear this in mind.
     */
    public WebUIPlugin copy() {
        try {
            return clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(WebUIPlugin.class.getName()).log(Level.SEVERE, null, ex);
            return new WebUIPlugin();
        }
    }

    /** Retrieve a web UI plugin descriptor out of the contents of a "package.json".
     * 
     * @param obj a JSON object of the plugin description
     * @return a web UI plugin descriptor out of the JSON object
     * @throws PluginFormatException if the JSON text contains bad information, or not enough of it
     */
    public static WebUIPlugin fromPackageJSON(JSONObject obj) throws PluginFormatException {
        WebUIPlugin plugin = new WebUIPlugin();
        plugin.name = obj.getString("name");
        plugin.version = obj.getString("version");
        plugin.description = obj.getString("description");
        
        JSONObject objDicoogle = obj.getJSONObject("dicoogle");
        if (objDicoogle == null) {
            throw new PluginFormatException("Missing dicoogle object in package.json");
        }
        
        plugin.slotId = objDicoogle.getString("slot-id");
        plugin.moduleFile = objDicoogle.getString("module-file");
        
        return plugin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getModuleFile() {
        return moduleFile;
    }

    public void setModuleFile(String moduleFile) {
        this.moduleFile = moduleFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSettings(JSONObject holder) {
        this.settings = holder;
    }
    
    /** Getter for the plugin's settings.
     * 
     * @return a JSON object containing the settings, empty object if no settings are stored
     */
    public JSONObject getSettings() {
        return this.settings != null ? this.settings : new JSONObject(false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
        
    @Override
    public String toString() {
        return "WebUIPlugin{" + "name=" + name + ", description=" + description
                + ", version=" + version + ", slotId=" + slotId
                + ", moduleFile=" + moduleFile + ", enabled=" + enabled + '}';
    }
    
}
