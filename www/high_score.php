<?php
header('Access-Control-Allow-Origin: *');

if (isset($_GET['s']) && !empty($_GET['l'])){
    if (strlen($_GET['l']) > 40) throw new \Exception("Level name waaaay too long");

    $pdo = new \PDO('sqlite:/var/sqlite/ld40.sqlite3');
    $pdo->setAttribute(\PDO::ATTR_ERRMODE, \PDO::ERRMODE_EXCEPTION);

    $pdo->exec("CREATE TABLE IF NOT EXISTS Scores (LevelName TEXT, ClientIP TEXT, Score INT)");

    $stmt = $pdo->prepare("INSERT INTO Scores VALUES (?, ?, ?)");

    $stmt->execute([$_GET['l'], $_SERVER['REMOTE_ADDR'], (int)$_GET['s']]);

    $stmt2 = $pdo->prepare("SELECT Score FROM Scores WHERE LevelName = ? ORDER BY Score DESC LIMIT 10");
    $stmt2->execute([$_GET['l']]);

    header('Content-Type: application/json');
    echo json_encode(['HighScores' => $stmt2->fetchAll(\PDO::FETCH_ASSOC)]);
}