package protobuf;

import com.protocol.VisualMemoryProtocol;



public enum ProtoType {

    // from client

    INSERT_RECORD((byte)0x02),
    UPDATE_RECORD((byte)0x3),
    GET_TOP_100((byte)0x4),


    // to client

    TOP100((byte)0x02, VisualMemoryProtocol.Top100.class),
    UNKNOWN((byte) 0x00);

    private final byte b;
    private Class protoClass;

    private ProtoType(byte b) {
        this.b = b;
    }

    private ProtoType(byte b, Class protoClass) {
        this.b = b;
        this.protoClass = protoClass;
    }


    public static ProtoType fromByte(byte b) {
        for (ProtoType code : values()) {
            if (code.b == b) {
                return code;
            }
        }
        return UNKNOWN;
    }

    public static ProtoType fromClass(Class c) {
        for (ProtoType type : values()) {
            if (type.protoClass != null && type.protoClass.equals(c)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public byte getByteValue() {
        return b;
    }
}
