package cz.novros.cp.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfoMapBuilder {

	Map<String, String> map;

	private InfoMapBuilder() {
		map = new HashMap<>();
	}

	@Nonnull
	public static InfoMapBuilder getBuilder() {
		return new InfoMapBuilder();
	}

	@Nonnull
	public InfoMapBuilder add(@Nonnull final String label, @Nullable final String value) {
		if (value != null) {
			map.put(label + ": ", value);
		}
		return this;
	}

	@Nonnull
	public Map<String, String> build() {
		return map;
	}
}
