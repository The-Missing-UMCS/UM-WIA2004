package com.umwia2004.solution.lab6_8.combined.domain;

import com.umwia2004.solution.util.Asserts;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrameMapping {
    public static final boolean STATUS_FREE = true;
    public static final boolean STATUS_OCCUPIED = false;

    private String mappedVpn;
    private boolean status;

    public static FrameMapping empty() {
        return new FrameMapping(null, STATUS_FREE);
    }

    /**
     * Maps the frame to a virtual page number (VPN).
     *
     * @param vpn The virtual page number to map to the frame.
     * @throws IllegalStateException If the frame is already occupied.
     */
    public void mapFrame(String vpn) {
        Asserts.isTrue(isFrameFree(), IllegalStateException.class, "Frame already occupied");
        this.mappedVpn = vpn;
        this.status = STATUS_OCCUPIED;
    }

    public void releaseFrame() {
        Asserts.isTrue(isFrameOccupied(), IllegalStateException.class, "Frame not occupied");
        this.mappedVpn = null;
        this.status = STATUS_FREE;
    }

    /**
     * Checks if the frame is free and can be allocated.
     *
     * @return True if the frame is free, false otherwise.
     */
    public boolean isFrameFree() {
        return mappedVpn == null && status == STATUS_FREE;
    }

    /**
     * Checks if the frame is occupied and can be released.
     *
     * @return True if the frame is occupied, false otherwise.
     */
    public boolean isFrameOccupied() {
        return mappedVpn != null && status == STATUS_OCCUPIED;
    }

    public boolean getStatus() {
        return status;
    }
}
