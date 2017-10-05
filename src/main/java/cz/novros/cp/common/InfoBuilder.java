package cz.novros.cp.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Joiner;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InfoBuilder {

	List<String> list;

	private InfoBuilder() {
		list = new ArrayList<>();
	}

	@Nonnull
	public static InfoBuilder getBuilder() {
		return new InfoBuilder();
	}

	@Nonnull
	public InfoBuilder add(@Nullable final String label, @Nullable final Object value) {
		if (value != null) {
			list.add((label == null ? "" : label + ": ") + value.toString());
		}
		return this;
	}

	@Nonnull
	public InfoBuilder add(@Nullable final String label, @Nullable final Object value, @Nonnull final String suffix) {
		if (value != null) {
			add(label, value.toString() + " " + suffix);
		}
		return this;
	}

	@Nullable
	public String build() {
		return getDisplayListToString(list);
	}

	@Nullable
	private static String getDisplayListToString(@Nullable final Collection<String> info) {
		return info == null || info.isEmpty() ? null : Joiner.on("    ").join(info);
	}
}
