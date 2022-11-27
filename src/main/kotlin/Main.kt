fun main(args: Array<String>) {
    // call manually...
    //    Day1().run()
    //    ...

    // ... or with black magic and time measuring
    for (day in 1..25) {
        try {
            val instanceOfDay = Class.forName("days.Day$day").getDeclaredConstructor().newInstance() as Day
            println("~~Day $day~~")
            val startTime = System.nanoTime()
            instanceOfDay.run()
            val endTime = System.nanoTime()
            val duration = (endTime - startTime) / 1000000
            println("-> " + duration + "ms")
        } catch (_: ClassNotFoundException) {
        }
    }
}
