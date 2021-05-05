package com.example.pictureoftheday.utils

import com.example.pictureoftheday.model.lop.Planet

fun getListOfPlanets() =

    mutableListOf(
        Pair(Planet(1, "Mercury", "Mercury is the closest planet to our Sun, the smallest of the eight planets, and one of the most extreme worlds in our Solar Systems. Named after the Roman messenger of the gods, the planet is one of a handful that can be viewed without the aid of a telescope. As such, it has played an active role in the mythological and astrological systems of many cultures.", 1), false),
        Pair(Planet(2, "Venus", "As the morning star, the evening star, and the brightest natural object in the sky (after the Moon), human beings have been aware of Venus since time immemorial", 2), false),
        Pair(Planet(3, "Earth", "In addition to being the birthplace of humanity and the cradle of human civilization, Earth is the only known planet in our Solar System that is capable of sustaining life", 3), false),
        Pair(Planet(4, "Mars", "Mars, otherwise known as the “Red Planet”, is the fourth planet of our Solar System and the second smallest (after Mercury)", 4), false),
        Pair(Planet(5, "Jupiter", "Ever since the invention of the telescope four hundred years ago, astronomers have been fascinated by the gas giant known as Jupiter", 5), false),
        Pair(Planet(6, "Saturn", "The farthest planet from the Sun that can be observed with the naked eye, the existence of Saturn has been known for thousands of years", 6), false),
        Pair(Planet(7, "Uranus", "Uranus, which takes its name from the Greek God of the sky, is a gas giant and the seventh planet from our Sun", 7), false),
        Pair(Planet(8, "Neptune", "Neptune is the eight planet from our Sun, one of the four gas giants, and one of the four outer planets in our Solar System", 8), false),

        )
