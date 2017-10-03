package cz.novros.cp.entity;

import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Parcel {

	@Id
	String parcelTrackingNumber;

	@Embedded
	Attributes attributes;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(value = "date")
	List<State> states;

	@Nullable
	public State getLastState() {
		return states == null || states.isEmpty() ? null : states.get(states.size() - 1);
	}
	
	public List<State> getStatesInReverse() {
		return Lists.reverse(states);
	}
}
