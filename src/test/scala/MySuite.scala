// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("Queen should lose 7 hit points on a hit") {
    val queen = new Queen()
    val startHitPoint = queen.hitPoints
    queen.takeHit()
    assertEquals(queen.hitPoints, 73)
  }

  test("Worker should lose 10 hit points on a hit") {
    val worker = new Worker()
    val startHitPoint = worker.hitPoints
    worker.takeHit()
    assertEquals(worker.hitPoints, 58)
  }

  test("Drone hit point should not drop below zero") {
    val drone = new Drone()
    val dummyQueen = new Queen()
    for(_ <- 1 to 10)drone.takeHit()
    assertEquals(drone.hitPoints, 0)
    assert(drone.isDead(dummyQueen))
  }

  test("Nest should start with 14 wasps in total") {
    val wasps = Nest.createWasps()
    assertEquals(wasps.length, 14)
  }

  test("Killing the Queen should kill all other wasps immediately") {
    val wasps = Nest.createWasps()
    val queen = wasps.head
    val others = wasps.tail
    // 12 * 7 = 84, queen starts with 80 hit points
    for(shot <- 1 to 12) {
       queen.takeHit()
    }
    assert(queen.isDead(queen))
    val allOthersAreDead = others.forall(w => w.isDead(queen))
    assert(allOthersAreDead)
  }

  test("Results CVS file should exist after game completion") {
    val file = new java.io.File("results.csv")
    assert(file.exists())
  }
}
