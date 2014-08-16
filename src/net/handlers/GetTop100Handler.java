package net.handlers;


import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.VisualMemoryProtocol;
import db.DBManager;
import db.entity.UserRecord;
import server.WorkerHelper;

import java.util.List;

public class GetTop100Handler extends IncomingMessageHandler{
    private VisualMemoryProtocol.Top100   packet;

    public GetTop100Handler(byte [] data){
        try {
            packet = VisualMemoryProtocol.Top100.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(WorkerHelper workerHelper) {

        List<UserRecord> userRecordsList = DBManager.getTop100();
        VisualMemoryProtocol.Top100.Builder top100Builder = VisualMemoryProtocol.Top100.newBuilder();

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
        System.out.println("GetTop100Handler handler()");
    }

}
