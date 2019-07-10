SELECT C.CLIENT_ID, C.SURNAME, CA.CLIENT_ACCOUNT_NUMBER, A_TYPE.DESCRIPTION, CA.DISPLAY_BALANCE
FROM CLIENT_ACCOUNT  CA
 INNER JOIN CLIENT C ON C.CLIENT_ID = CA.CLIENT_ID
 INNER JOIN ACCOUNT_TYPE A_TYPE ON A_TYPE.ACCOUNT_TYPE_CODE = CA.ACCOUNT_TYPE_CODE
 INNER JOIN (
    SELECT INNER_CA.CLIENT_ID, MAX(INNER_CA.DISPLAY_BALANCE) DISPLAY_BALANCE
    FROM CLIENT_ACCOUNT INNER_CA
      INNER JOIN ACCOUNT_TYPE INNER_AT_TYPE ON INNER_AT_TYPE.ACCOUNT_TYPE_CODE = INNER_CA.ACCOUNT_TYPE_CODE
    WHERE INNER_AT_TYPE.TRANSACTIONAL = TRUE
    GROUP BY INNER_CA.CLIENT_ID
  ) BAL ON BAL.CLIENT_ID = C.CLIENT_ID AND BAL.DISPLAY_BALANCE = CA.DISPLAY_BALANCE
GROUP BY C.CLIENT_ID, CA.CLIENT_ACCOUNT_NUMBER