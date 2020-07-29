/*
 * Copyright 2016-2020 Chronicle Software
 *
 * https://chronicle.software
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

import net.openhft.chronicle.core.io.IORuntimeException;

/**
 * thrown when the TcpChannelHub drops its connection to the server
 */
// TODO Move to network where it is used.
@Deprecated
public class ConnectionDroppedException extends IORuntimeException {
    public ConnectionDroppedException(String message) {
        super(message);
    }

    public ConnectionDroppedException(Throwable e) {
        super(e);
    }

    public ConnectionDroppedException(String s, Throwable e) {
        super(s, e);
    }
}
