package protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import net.handlers.*;




public class ProtoFactory {

    public static IncomingMessageHandler createHandler(byte data[], ProtoType type) throws InvalidProtocolBufferException {


            switch (type) {


            case  INSERT_RECORD:
                return new InsertRecordHandler(data);
            case UPDATE_RECORD:
                return new UpdateRecordHandler(data);
            case GET_TOP_100:
                return  new GetTop100Handler(data);
            default:
                System.err.println("GET BED PACKET " + type);

        }


        return null;
    }


}
