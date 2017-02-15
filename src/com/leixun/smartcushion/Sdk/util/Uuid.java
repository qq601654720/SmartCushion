package com.leixun.smartcushion.Sdk.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yaohaijun on 16-02-01.
 */
public class Uuid {
    public static List<UUID> parseFromAdvertisementData(byte[] advertisedData) {
        List<UUID> uuids = new ArrayList<UUID>();

        ByteBuffer buffer = ByteBuffer.wrap(advertisedData).order(ByteOrder.LITTLE_ENDIAN);
        while (buffer.remaining() > 2) {
            byte length = buffer.get();
            if (length == 0) break;

            byte type = buffer.get();
            switch (type) {
//                case 0x02: // Partial list of 16-bit UUIDs
//                case 0x03: // Complete list of 16-bit UUIDs
//                case 0x14: // List of 16-bit Service Solicitation UUIDs
//                    while (length >= 2) {
//                        uuids.add(UUID.fromString(String.format("%08x-B5A3-F393-E0A9-E50E24DCCA9E", buffer.getShort())));
//                    	L.e("uuids============="+uuids);
//
//                        length -= 2;
//                    }
//                    break;
//                case 0x04: // Partial list of 32 bit service UUIDs
//                case 0x05: // Complete list of 32 bit service UUIDs
//                  while (length >= 4) {
//                	  	uuids.add(UUID.fromString(String.format(
//                        "%08x-B5A3-F393-E0A9-E50E24DCCA9E", buffer.getInt())));
//                		L.e("uuids============="+uuids);
//
//                    length -= 4;
//                  }
//                  break;
                case 0x06: // Partial list of 128-bit UUIDs
                case 0x07: // Complete list of 128-bit UUIDs
                    while (length >= 16) {
                        long lsb = buffer.getLong();
                        long msb = buffer.getLong();
                        uuids.add(new UUID(msb, lsb));
                        length -= 16;
//                        L.e("uuids============="+uuids);
                    }

                    break;

                default:
                    buffer.position(buffer.position() + length - 1);
                    break;
            }
        }

        return uuids;
    }
}
