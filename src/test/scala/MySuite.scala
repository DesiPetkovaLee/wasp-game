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
    for(_ <- 1 to 10)drone.takeHit()
    assertEquals(drone.hitPoints, 0)
    assert(drone.isDead)
  }

  test("Nest should start with 14 wasps in total") {
    val wasps = Nest.createWasps()
    assertEquals(wasps.length, 14)
  }
}
