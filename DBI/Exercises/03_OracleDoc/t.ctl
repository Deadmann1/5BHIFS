LOAD DATA
	INFILE 'c:\OracleText\02contextmarkup.dat'
	INTO TABLE docs
	REPLACE
		FIELDS TERMINATED BY ';'
		(id INTEGER,
		 title CHAR,
		 text_file FILLER CHAR,
		 text LOBFILE(text_file) TERMINATED BY EOF)