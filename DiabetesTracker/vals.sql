CREATE TABLE `vals` (
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bs` int(11) NOT NULL,
  `carbs` double NOT NULL,
  `insulin` double NOT NULL,
  `notes` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;