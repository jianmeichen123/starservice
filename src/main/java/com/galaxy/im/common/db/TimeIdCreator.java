package com.galaxy.im.common.db;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.galaxy.im.common.CUtils;

public class TimeIdCreator implements IdCreator {
	private ConcurrentMap<String, AtomicLong> automicLongMap = new ConcurrentHashMap<String, AtomicLong>();
	private Integer idc = null;

	public Long nextId(String sKey) throws Exception {
		if (CUtils.get().stringIsEmpty(sKey)) {
			throw new Exception("sKey must have value");
		} else {
			String atomicKey = sKey;
			AtomicLong seqAtomic = automicLongMap.get(atomicKey);
	
			if (seqAtomic == null) {
				AtomicLong memAtomic = new AtomicLong(0);
				seqAtomic = automicLongMap.putIfAbsent(atomicKey, memAtomic);
				seqAtomic = seqAtomic == null ? memAtomic : seqAtomic;
			}
	
			return TimeIdHelper.getIdByDate(System.currentTimeMillis(), seqAtomic, idc);
	
		}
	}

	public TimeIdCreator(Integer idc) {
		this.idc = idc;
	}
}
