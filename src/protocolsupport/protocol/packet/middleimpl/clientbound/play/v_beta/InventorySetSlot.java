package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.SlotDoesntExistException;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		String locale = cache.getAttributesCache().getLocale();
		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			codec.write(create(version, locale, windowId, slot, itemstack));
			return;
		}
		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			//TODO: remap for versions that don't actually support this special window id
			codec.write(create(version, locale, windowId, slot, itemstack));
			return;
		}
		if ((windowId == WINDOW_ID_PLAYER_HOTBAR) && (slot >= 36) && (slot < 45)) {
			codec.write(create(version, locale, windowId, slot, itemstack));
			return;
		}

		if (!windowCache.isValidWindowId(windowId)) {
			return;
		}

		try {
			int windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			codec.write(create(
				version, locale,
				WindowRemapper.getClientSlotWindowId(windowSlot),
				WindowRemapper.getClientSlotSlot(windowSlot), itemstack
			));
		} catch (SlotDoesntExistException e) {
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData windowslot = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_SLOT);
		windowslot.writeByte(windowId);
		windowslot.writeShort(slot);
		ItemStackSerializer.writeItemStack(windowslot, version, locale, itemstack);
		return windowslot;
	}

}