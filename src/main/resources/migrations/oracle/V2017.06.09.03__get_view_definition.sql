CREATE FUNCTION get_view_definition(p_view_name VARCHAR2)
  RETURN VARCHAR2
  AS
          l_data LONG;
  BEGIN
      SELECT text
        INTO l_data
        FROM user_views
       WHERE view_name = p_view_name;

      RETURN SUBSTR(l_data, 1, 4000);
  END;