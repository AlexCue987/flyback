CREATE FUNCTION get_search_condition(p_constraint_name VARCHAR2)
  RETURN VARCHAR2
  AS
          l_data LONG;
  BEGIN
      SELECT search_condition
        INTO l_data
        FROM user_constraints
       WHERE constraint_name = p_constraint_name;

      RETURN SUBSTR(l_data, 1, 100);
  END;