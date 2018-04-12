package net.muliba.eyeopener.ui.vo

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by fancyLou on 12/04/2018.
 */


data class VideoVO(
        var feed: String?,
        var title: String?,
        var description: String?,
        var duration: Long?,
        var playUrl: String?,
        var category: String?,
        var blurred: String?,
        var collect: Int?,
        var share: Int?,
        var reply: Int?
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(feed)
        writeString(title)
        writeString(description)
        writeValue(duration)
        writeString(playUrl)
        writeString(category)
        writeString(blurred)
        writeValue(collect)
        writeValue(share)
        writeValue(reply)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VideoVO> = object : Parcelable.Creator<VideoVO> {
            override fun createFromParcel(source: Parcel): VideoVO = VideoVO(source)
            override fun newArray(size: Int): Array<VideoVO?> = arrayOfNulls(size)
        }
    }
}