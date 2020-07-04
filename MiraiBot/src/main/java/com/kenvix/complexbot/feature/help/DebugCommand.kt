package com.kenvix.complexbot.feature.help

import com.kenvix.complexbot.BotCommandFeature
import com.kenvix.complexbot.CallBridge
import com.kenvix.complexbot.callBridge
import com.kenvix.moecraftbot.ng.Defines
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.isFriend
import net.mamoe.mirai.contact.isMuted
import net.mamoe.mirai.message.MessageEvent
import java.text.SimpleDateFormat
import java.util.*

object DebugCommand : BotCommandFeature {
    override suspend fun onMessage(msg: MessageEvent) {
        val text = StringBuilder()
        text.appendln("MoeNet Complex Bot v0.1")
        text.appendln("Written by Kenvix | Github: kenvix/ComplexBot")
        text.appendln("Powered by mamoe/mirai and MoeCraft Bot Framework")
        text.appendln("Java ${System.getProperty("java.version")} | Kotlin ${KotlinVersion.CURRENT}")
        text.appendln(callBridge.backendClient.aboutInfo)
        text.appendln("Platform: ${System.getProperty("os.name")} ")
        text.appendln("Time: " + SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis())))

        val total = Runtime.getRuntime().totalMemory().toInt() / 1024 / 1024
        val free = Runtime.getRuntime().freeMemory().toInt() / 1024 / 1024
        val max = (Runtime.getRuntime().maxMemory() / 1024 / 1024).toInt()
        text.appendln("Memory：$free/$total MiB (Max $max)")
        text.appendln("===Sender Info===")
        text.append(msg.sender.run {
            when(this) {
                is Friend -> "Friend: $id($nick)\nAvatar: $avatarUrl"
                is Member -> "Member: $id($nick)\nAvatar: $avatarUrl\nSpecialTitle: $specialTitle | " +
                        "NameCard: $nameCard\nMute $isMuted, $muteTimeRemaining\nPermission: ${this.permission}" +
                        " | isFriend: ${this.isFriend} \nGroup ${group.id}(${group.name})"
                else -> "Unknown"
            }
        })

        msg.reply(text.toString())
    }
}