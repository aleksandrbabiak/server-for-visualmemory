package net.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protocol.VisualMemoryProtocol;
import db.DBManager;
import db.entity.UserRecord;
import server.WorkerHelper;

import java.util.List;


public class UpdateRecordHandler extends IncomingMessageHandler {

    private VisualMemoryProtocol.UpdateRecord   packet;

    public UpdateRecordHandler(byte [] data){
        try {
            packet = VisualMemoryProtocol.UpdateRecord.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handle(WorkerHelper workerHelper) {

        int userGlobalID =   packet.getGlobalUserID();
        String date = packet.getDate();
        double k_factod = packet.getKFactor();

        DBManager.updateUserRecord(date,k_factod,userGlobalID);
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
        System.out.println("UpdateRecordHandler handler()");

    }
}
