package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import db.DBManager;
import db.entity.UserRecord;
import server.WorkerHelper;
import com.protocol.VisualMemoryProtocol;

import java.util.List;


public class InsertRecordHandler extends IncomingMessageHandler {
    private VisualMemoryProtocol.InsertRecord   packet;

    public InsertRecordHandler(byte [] data){
        try {
            packet = VisualMemoryProtocol.InsertRecord.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(WorkerHelper workerHelper) {

     int userGlobalID =  DBManager.insertGlobalRecord(packet.getUsreName(),packet.getDate(),packet.getKFactor());
     List<UserRecord> userRecordsList = DBManager.getTop100();

      VisualMemoryProtocol.Top100.Builder top100Builder = VisualMemoryProtocol.Top100.newBuilder();

        top100Builder.setGlobalUserID(userGlobalID);

        for(UserRecord userRecord: userRecordsList){
            VisualMemoryProtocol.InsertRecord insertRecord = VisualMemoryProtocol.InsertRecord.newBuilder()
                    .setGlobalUserID(userRecord.getGlobalUserID())
                    .setUsreName(userRecord.getName())
                    .setDate(userRecord.getDate())
                    .setKFactor(userRecord.getK_factor()).build();
            top100Builder.addNewRecord(insertRecord);
        }

        VisualMemoryProtocol.Top100 top100Messages = top100Builder.build();

        workerHelper.sendPacket(top100Messages);
        System.out.println("InsertRecordHandler handler()");

    }
}
