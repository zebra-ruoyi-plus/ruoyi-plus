package com.zebra.common.redis.kyro;

import org.springframework.data.redis.serializer.RedisSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

/**
 * Kryo Spring data redis 序列化实现
 *
 * @author zebra <br/>
 *         2019-07-26
 *
 */
public class KryoSerializer implements RedisSerializer<Object> {
	static final int KRYO_OUTPUT_BUFFER_SIZE = 1024 * 1024;
	static final int KRYO_OUTPUT_MAX_BUFFER_SIZE = Integer.MAX_VALUE;

	KryoFactory factory = new KryoFactory() {
		@Override
		public Kryo create() {
			Kryo kryo = new Kryo();
			return kryo;
		}
	};

	KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

	@Override
	public byte[] serialize(Object o) {
		Kryo kryo = null;
		Output outPut = null;
		try {
			kryo = pool.borrow();
			outPut = new Output(KRYO_OUTPUT_BUFFER_SIZE, KRYO_OUTPUT_MAX_BUFFER_SIZE);
			kryo.writeClassAndObject(outPut, o);
			outPut.flush();
			return outPut.toBytes();
		} finally {
			pool.release(kryo);
			if (outPut != null) {
				outPut.close();
			}
		}
	}

	@Override
	public Object deserialize(byte[] ba) {
		Kryo kryo = null;
		Input input = null;
		try {
			if (ba == null || ba.length == 0) {
				return null;
			}
			kryo = pool.borrow();
			input = new Input(ba);
			return kryo.readClassAndObject(input);
		} finally {
			pool.release(kryo);
			if (input != null) {
				input.close();
			}
		}
	}
}