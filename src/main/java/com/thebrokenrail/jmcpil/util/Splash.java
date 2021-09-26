package com.thebrokenrail.jmcpil.util;

import java.util.Random;

public class Splash {
    private static final String[] SPLASHES = new String[]{
            "Sexy!",
            "We Fixed The Rail!",
            "Teh",
            "Who Broke The Rail?",
            "The Work Of Notch!",
            "The World Of Notch!",
            "WDYM Discontinued?",
            "Oh, OK, Pigmen...",
            "Check Out The Non-Existant Far Lands!",
            "mcpi.tk!",
            "Come Back @Alvarito!",
            "Classic!",
            "Wow! Modded MCPI!",
            "Now With Fly-Hacks!",
            "Where Is My Pie?",
            "I Was Promised Pie!",
            "@Banana",
            "BANANA!",
            "To \"Na\" Or Not To \"Na\", That Is The Question!",
            "Not Minecraft Java!",
            "Not Minecraft Bedrock (Technically)!",
            "Definitely Not Minecraft Console!",
            "Oh Yeah, That Version...",
            "Join The Discord!",
            "Segmentation fault",
            "Segmentation fault (core dumped)",
            "qemu: uncaught target signal 11 (Segmentation fault) - core dumped",
            "It's The Segment's Fault!",
            "As Seen On Discord!",
            "Obfuscated!",
            "As Not Seen On TV!",
            "Why Must You Hurt Me?",
            "Who Is StevePi?",
            "Made By Notch!",
            "Open-Source!",
            "FREE!",
            "RIP MCPIL-Legacy, 2020-2020",
            "Awesome Community!",
            "Awesome!",
            "Minecraft!",
            "Now in 3D!",
            "Pretty!",
            "Singleplayer!",
            "Survive!",
            "Try it!",
            "你好中国",
            "한국 안녕하세요!",
            "日本ハロー！",
            "Привет Россия!",
            "Γεια σου Ελλάδα!",
            "Water proof!",
            "Supercalifragilisticexpialidocious!",
            "Pixels!",
            "Now With Multiplayer!",
            "Keyboard compatible!",
            "Exploding creepers!",
            "Enhanced!",
            "Don't look directly at the bugs!",
            "Fantasy!",
            "Create!",
            "Casual gaming!"
    };

    /**
     * Get Random Splash Text
     * @return Splash Text
     */
    public static String get() {
        Random random = new Random();
        int i = random.nextInt(SPLASHES.length);
        return SPLASHES[i];
    }
}
