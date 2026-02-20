import scala.util.Random
import scala.io.StdIn.readLine
import scala.annotation.tailrec
import java.io.{FileWriter, PrintWriter}
import java.io.File

trait Wasp:
  val deduction: Int
  var hitPoints: Int

  def isDead: Boolean = hitPoints <= 0

  def takeHit(): Unit = 
    hitPoints = Math.max(0, hitPoints - deduction)

  override def toString: String =
    val name = this.getClass.getSimpleName
    s"$name: hitPoints = ($hitPoints hit points)"

class Queen extends Wasp:
  val deduction = 7
  var hitPoints = 80

class Worker extends Wasp:
  val deduction = 10
  var hitPoints = 68

class Drone extends Wasp:
  val deduction = 12
  var hitPoints = 60

object Nest:
  def createWasps(): List[Wasp] = 
    val queen = List(new Queen())
    val workers = List.fill(5)(new Worker())
    val drones = List.fill(8)(new Drone())
    queen ++ workers ++ drones

  def displayStatus(wasps: List[Wasp]): Unit =
    println("\nScanning the nest... Here's what's left ->" )
    wasps.foreach(println)

object Game:
  def fire(wasps: List[Wasp]): Unit =
    val aliveWasps = wasps.filter(w => !w.isDead)

    if aliveWasps.nonEmpty then
      val random = Random.nextInt(aliveWasps.length)
      val target = aliveWasps(random)

      println(s"\n One ${target.getClass.getSimpleName} was struck by your fire.")
      target.takeHit()

@main def startWaspGame(): Unit =
  println("Welcome to the Wasp Game!")
  println("Commands: 'fire' to shoot, 'auto' for rapid fire, 'restart' to reset, or 'quit' to exit.")
  setupNewGame()

def setupNewGame(): Unit =
  val name = readLine("\nWho's the wasp slayer today? ")
  val startTime = System.currentTimeMillis()
  val nestWasps = Nest.createWasps()
  processCommand(nestWasps, name, startTime, 0)

@tailrec
def processCommand(wasps: List[Wasp], name: String, startTime: Long, shots: Int): Unit =
  val queen = wasps.head
  val input = readLine("Enter your command: ").toLowerCase.trim

  input match
    case "fire" => 
      if queen.isDead || wasps.forall(w => w.isDead) then
        println("\nThe game is already over! Type 'restart' to play again.")
        processCommand(wasps, name, startTime, shots)
      else 
        Game.fire(wasps)
        Nest.displayStatus(wasps)

        val newShots = shots + 1

        if queen.isDead || wasps.forall(w => w.isDead) then
          println(s"\nGAME OVER, $name!")
          println(s"You took $newShots shots.")
          saveToCSV(name, startTime, newShots)
          println("Type 'restart' to play again or 'quit' to exit.")
          processCommand(wasps, name, startTime, newShots)
        else
          processCommand(wasps, name, startTime, newShots)

    case "auto" => 
      println(s"Auto-mode activated, $name! Taking down wasps at light speed! ")
      val finalShots = runAutoFire(wasps, shots)
      Nest.displayStatus(wasps)
      println(s"\nMISSION ACCOMPLISHED, $name!")
      saveToCSV(name, startTime, finalShots)
      println("Type 'restart' to play again or 'quit' to exit.")
      processCommand(wasps, name, startTime, finalShots)

    case "restart" => 
      setupNewGame()

    case "quit" =>
      println(s"Thanks for playing, $name!")
      
    case _ => 
      println("Invalid command. Please enter 'fire', 'auto', 'restart', or 'quit'.")  
      processCommand(wasps, name, startTime, shots)

@tailrec
def runAutoFire(wasps: List[Wasp], count: Int): Int =
  val queen = wasps.head
  if queen.isDead || wasps.forall(w => w.isDead) then
    count
  else 
    Game.fire(wasps) 
    runAutoFire(wasps, count + 1) 

// saving the results of the game in a CSV file
def saveToCSV(name: String, startTime: Long, finalShots: Int): Unit = 
  val endTime: Long = System.currentTimeMillis()
  val duration: Long = (endTime - startTime) / 1000

  val fileName = "results.csv"
  val file = new File(fileName)

  val isNewFile = !file.exists() || file.length() == 0

  val fileWriter = new FileWriter("results.csv", true)
  val printWriter = new PrintWriter(fileWriter)

  try 
    if isNewFile then 
      printWriter.println("Player Name, Shots Fired, Time (seconds)") 
      printWriter.println(s"$name,$finalShots,$duration")
      println(s"\n[Logged to CSV: $name, $finalShots shots, $duration seconds]")
  finally 
    printWriter.close()
