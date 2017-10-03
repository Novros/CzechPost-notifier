package cz.novros.cp.dao;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.data.repository.CrudRepository;

import cz.novros.cp.entity.Parcel;


public interface ParcelRepository extends CrudRepository<Parcel, String> {

	@Nullable
	Collection<Parcel> findByParcelTrackingNumberIn(@Nonnull final Collection<String> parcelTracingNumbers);
}
