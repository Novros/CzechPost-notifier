package cz.novros.cp.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BigDecimalComparator {

	private static final int COMPARATOR_SCALE = 2;

	public static boolean isZero(@Nullable final BigDecimal a) {
		if (a == null) {
			return true;
		}

		return isEqual(a, BigDecimal.ZERO);
	}

	public static boolean isEqual(@Nullable BigDecimal a, @Nullable BigDecimal b) {
		if (a == null && b == null) {
			return true;
		} else if (a == null || b == null) {
			return false;
		}

		a = a.setScale(COMPARATOR_SCALE, RoundingMode.HALF_UP);
		b = b.setScale(COMPARATOR_SCALE, RoundingMode.HALF_UP);

		return a.compareTo(b) == 0;
	}
}
