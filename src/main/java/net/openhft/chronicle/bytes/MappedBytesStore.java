/*
 * Copyright 2016 higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.bytes;

import net.openhft.chronicle.core.OS;
import org.jetbrains.annotations.NotNull;

/**
 * BytesStore to wrap memory mapped data.
 */
public class MappedBytesStore extends NativeBytesStore<Void> {
    private final MappedFile mappedFile;
    private final long start;
    private final long safeLimit;

    protected MappedBytesStore(MappedFile mappedFile, long start, long address, long capacity, long safeCapacity) throws IllegalStateException {
        super(address, start + capacity, new OS.Unmapper(address, capacity, mappedFile), false);
        this.mappedFile = mappedFile;
        this.start = start;
        this.safeLimit = start + safeCapacity;
        mappedFile.reserve(this);
    }

    @Override
    protected synchronized void performRelease() {
        super.performRelease();
        mappedFile.release(this);
    }

    @NotNull
    @Override
    public VanillaBytes<Void> bytesForWrite() throws IllegalStateException {
        return new VanillaBytes<>(this);
    }

    @Override
    public boolean inside(long offset) {
        return start <= offset && offset < safeLimit;
    }

    @Override
    public boolean inside(long offset, long buffer) {
        // this is correct that it uses the maximumLimit, yes it is different than the method above.
        return start <= offset && offset + buffer < maximumLimit;
    }

    @Override
    public long safeLimit() {
        return safeLimit;
    }

    @Override
    public byte readByte(long offset) {
        return memory.readByte(address - start + offset);
    }

    @NotNull
    @Override
    public NativeBytesStore<Void> writeOrderedInt(long offset, int i) {
        memory.writeOrderedInt(address - start + offset, i);
        return this;
    }

    @Override
    protected long translate(long offset) {
        assert offset >= start;
        assert offset < maximumLimit;

        return offset - start;
    }

    @Override
    public long start() {
        return start;
    }

    @Override
    public long readPosition() {
        return start();
    }

    @Override
    public @NotNull String toString() {
        return getClass().getSimpleName() + "{ address: " + Long.toHexString(address) + ", capacity: " + Long.toHexString(capacity()) + " }";
    }
}
