/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2017 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
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
package org.sleuthkit.autopsy.experimental.autoingest;

import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorCallback;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorProgressMonitor;
import org.sleuthkit.autopsy.coreutils.Logger;

/*
 * A runnable that adds an archive data source to the case database.
 */
public class AddArchiveTask implements Runnable {

    private final Logger logger = Logger.getLogger(AddArchiveTask.class.getName());
    private final String deviceId;
    private final String imagePath;
    private final DataSourceProcessorProgressMonitor progressMonitor;
    private final DataSourceProcessorCallback callback;
    private boolean criticalErrorOccurred;

    /*
     * The cancellation requested flag and SleuthKit add image process are
     * guarded by a monitor (called a lock here to avoid confusion with the
     * progress monitor) to synchronize cancelling the process (setting the flag
     * and calling its stop method) and calling either its commit or revert
     * method. The built-in monitor of the add image process can't be used for
     * this because it is already used to synchronize its run (init part),
     * commit, revert, and currentDirectory methods.
     *
     * TODO (AUT-2021): Merge SleuthkitJNI.AddImageProcess and AddImageTask
     */
    private final Object tskAddImageProcessLock;

    /**
     * Constructs a runnable task that adds an image to the case database.
     *
     * @param deviceId An ASCII-printable identifier for the device associated
     * with the data source that is intended to be unique across multiple cases
     * (e.g., a UUID).
     * @param imagePath Path to the image file.
     * @param progressMonitor Progress monitor to report progress during
     * processing.
     * @param callback Callback to call when processing is done.
     */
    AddArchiveTask(String deviceId, String imagePath, DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
        this.deviceId = deviceId;
        this.imagePath = imagePath;
        this.callback = callback;
        this.progressMonitor = progressMonitor;
        tskAddImageProcessLock = new Object();
    }

    /**
     * Adds the archive to the case database.
     */
    @Override
    public void run() {

    }
    
    
    /*
     * Attempts to cancel adding the image to the case database.
     */
    public void cancelTask() {
        
    }
}
