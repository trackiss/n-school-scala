package com.github.trackiss
package chapter10

final case class Config(
    wordsFilePath: String,
    urlsFilePath: String,
    outputDirPath: String,
    numOfDownloader: Int
)
