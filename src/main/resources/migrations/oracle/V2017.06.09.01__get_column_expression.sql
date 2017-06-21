CREATE FUNCTION get_column_expression(p_index_name VARCHAR2, p_column_position NUMBER)
  RETURN VARCHAR2
  AS
          l_data LONG;
  BEGIN
      SELECT column_expression
        INTO l_data
        FROM user_ind_expressions
       WHERE index_name = p_index_name
       AND column_position = p_column_position;

      RETURN SUBSTR(l_data, 1, 100);
  END;

