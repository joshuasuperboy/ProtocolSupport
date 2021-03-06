package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddlePickItem extends ServerBoundMiddlePacket {

	public MiddlePickItem(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;

	@Override
	public void writeToServer() {
		codec.read(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_PICK_ITEM);
		VarNumberSerializer.writeVarInt(serializer, slot);
		return serializer;
	}

}
