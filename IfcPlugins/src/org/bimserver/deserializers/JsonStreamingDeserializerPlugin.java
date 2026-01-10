package org.bimserver.deserializers;

import java.util.Set;

import org.bimserver.emf.Schema;
import org.bimserver.models.store.ObjectDefinition;
import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.PluginContext;
import org.bimserver.plugins.deserializers.StreamingDeserializer;
import org.bimserver.plugins.deserializers.StreamingDeserializerPlugin;
import org.bimserver.shared.exceptions.PluginException;

public class JsonStreamingDeserializerPlugin implements StreamingDeserializerPlugin {

	@Override
	public void init(PluginContext pluginContext, PluginConfiguration systemSettings) throws PluginException {
	}

	@Override
	public ObjectDefinition getUserSettingsDefinition() {
		return null;
	}

	@Override
	public ObjectDefinition getSystemSettingsDefinition() {
		return null;
	}

	@Override
	public StreamingDeserializer createDeserializer(PluginConfiguration pluginConfiguration) {
		return new JsonStreamingDeserializer();
	}

	@Override
	public boolean canHandleExtension(String extension) {
		return extension.contentEquals("json");
	}

	@Override
	public Set<Schema> getSupportedSchemas() {
		return Schema.getIfcSchemas();
	}
}
