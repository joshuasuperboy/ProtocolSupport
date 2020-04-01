package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLook;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EntityLook extends MiddleEntityLook {

	public EntityLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitylook = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_LOOK);
		entitylook.writeInt(entityId);
		entitylook.writeByte(yaw);
		entitylook.writeByte(pitch);
		codec.write(entitylook);
	}

}