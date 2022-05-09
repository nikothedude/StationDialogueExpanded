package com.fs.starfarer.api.characters

object SDE_personVariables {
    @JvmStatic
    fun personVoiceMult(person: PersonAPI): Float {
        val personalityBribeMult: Float = when (person.voice) {
            "soldier" -> 1.5f
            "spacer" -> 1.0f
            "faithful" -> 2.2f
            "pather" -> 0.8f
            "business" -> 1.1f
            "official" -> 3f
            "scientist" -> 1.4f
            "villain" -> 0.7f
            "aristo" -> 1.5f
            else -> 1.0f
        }
        return personalityBribeMult
    }
}