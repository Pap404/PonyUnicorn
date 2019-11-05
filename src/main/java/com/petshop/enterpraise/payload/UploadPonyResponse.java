package com.petshop.enterpraise.payload;

public class UploadPonyResponse {
    private String ponyFileName;
    private String ponyFileDownloadUri;
    private String ponyFileType;
    private long ponySize;

    public UploadPonyResponse(String ponyFileName, String ponyFileDownloadUri, String ponyFileType, long ponySize) {
        this.ponyFileName = ponyFileName;
        this.ponyFileDownloadUri = ponyFileDownloadUri;
        this.ponyFileType = ponyFileType;
        this.ponySize = ponySize;
    }

    public String getPonyFileName() {
        return ponyFileName;
    }

    public void setPonyFileName(String ponyFileName) {
        this.ponyFileName = ponyFileName;
    }

    public String getPonyFileDownloadUri() {
        return ponyFileDownloadUri;
    }

    public void setPonyFileDownloadUri(String ponyFileDownloadUri) {
        this.ponyFileDownloadUri = ponyFileDownloadUri;
    }

    public String getPonyFileType() {
        return ponyFileType;
    }

    public void setPonyFileType(String ponyFileType) {
        this.ponyFileType = ponyFileType;
    }

    public long getPonySize() {
        return ponySize;
    }

    public void setPonySize(long ponySize) {
        this.ponySize = ponySize;
    }
}
