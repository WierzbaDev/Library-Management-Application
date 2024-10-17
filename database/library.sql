-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 17 Paź 2024, 09:58
-- Wersja serwera: 10.4.27-MariaDB
-- Wersja PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `library`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `book`
--

CREATE TABLE `book` (
  `ID` int(11) NOT NULL,
  `Title` varchar(255) NOT NULL DEFAULT 'NOT NULL',
  `Author` varchar(255) NOT NULL DEFAULT 'NOT NULL',
  `Available` tinyint(1) NOT NULL DEFAULT 1,
  `Available_from` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `book`
--

INSERT INTO `book` (`ID`, `Title`, `Author`, `Available`, `Available_from`) VALUES
(16, '1984', 'George Orwell', 1, NULL),
(17, 'To Kill a Mockingbird', 'Harper Lee', 0, '2024-10-17'),
(18, 'The Great Gatsby', 'F. Scott Fitzgerald', 1, NULL),
(19, 'Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', 1, NULL),
(20, 'The Catcher in the Rye', 'J.D. Salinger', 0, '2024-10-17'),
(21, 'Pride and Prejudice', 'Jane Austen', 1, NULL),
(22, 'The Lord of the Rings', 'J.R.R. Tolkien', 0, '2024-10-17'),
(23, 'The Alchemist', 'Paulo Coelho', 1, NULL),
(24, ' Brave New World', 'Aldous Huxley', 1, NULL),
(25, ' The Hobbit', 'J.R.R. Tolkien', 1, NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `loans`
--

CREATE TABLE `loans` (
  `ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `loan_date` date NOT NULL,
  `return_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `loans`
--

INSERT INTO `loans` (`ID`, `user_id`, `book_id`, `loan_date`, `return_date`) VALUES
(38, 45, 22, '2024-10-17', NULL),
(39, 45, 17, '2024-10-17', NULL),
(40, 46, 20, '2024-10-17', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `reservations`
--

CREATE TABLE `reservations` (
  `ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `reservation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `reservations`
--

INSERT INTO `reservations` (`ID`, `user_id`, `book_id`, `reservation_date`) VALUES
(48, 46, 22, '2024-10-17');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(500) NOT NULL,
  `Role` enum('STUDENT','ADMIN') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`ID`, `Name`, `Email`, `Password`, `Role`) VALUES
(45, 'Mateusz', 'mat.student@library.com', '$2a$10$z9KR/aFc6nNkjq1l51.WMul.wQPv40jHR79BITrp5j/kKPdjEZZxS', 'STUDENT'),
(46, 'Artur', 'art.student@library.com', '$2a$10$I5A3ANiQmnrgCrHTre4QduQdXVeG/mz7RpwazVo2hEuXv1dsZWbcy', 'STUDENT'),
(47, 'Piotr', 'pio.admin@library.com', '$2a$10$M/m9Zsp7ijJFuIKQOD9UKOpDPtOzuYfGhHaiIsYdVJ4SknW7.IXFW', 'ADMIN'),
(48, 'Andrzej', 'and.admin@library.com', '$2a$10$BIKHjtt8TGivDfY5tVPAWunIuqbSiaAQTpTVay91X/pMaT..5YSFe', 'ADMIN');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`ID`);

--
-- Indeksy dla tabeli `loans`
--
ALTER TABLE `loans`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indeksy dla tabeli `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indeksy dla tabeli `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `book`
--
ALTER TABLE `book`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT dla tabeli `loans`
--
ALTER TABLE `loans`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT dla tabeli `reservations`
--
ALTER TABLE `reservations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `loans`
--
ALTER TABLE `loans`
  ADD CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`ID`),
  ADD CONSTRAINT `loans_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`ID`);

--
-- Ograniczenia dla tabeli `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`ID`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
