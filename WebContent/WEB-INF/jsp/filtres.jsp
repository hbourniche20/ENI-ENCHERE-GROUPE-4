<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script text="javascript">
	$(document).ready(function() {
		if($('#achats').is(':checked')) {
			enableCheckbox(['#encheresOuvertes', '#mesEncheres', '#mesEncheresRemportees']);
		    disabledCheckbox(['#ventesEnCours', '#ventesNonDebutees', '#ventesTerminees']);
		} else {
			enableCheckbox(['#ventesEnCours', '#ventesNonDebutees', '#ventesTerminees']);
			disabledCheckbox(['#encheresOuvertes', '#mesEncheres', '#mesEncheresRemportees']);
		}
	});
	
	$('#achats').click(function() {
		enableCheckbox(['#encheresOuvertes', '#mesEncheres', '#mesEncheresRemportees']);
	    disabledCheckbox(['#ventesEnCours', '#ventesNonDebutees', '#ventesTerminees']);
	});
	
	$('#ventes').click(function() {
		enableCheckbox(['#ventesEnCours', '#ventesNonDebutees', '#ventesTerminees']);
		disabledCheckbox(['#encheresOuvertes', '#mesEncheres', '#mesEncheresRemportees']);
	});
		
	function enableCheckbox(values) {
		$.each(values, function(index, value) {
			$(value).prop('disabled', false);
		});
	}
		
	function disabledCheckbox(values) {
		$.each(values, function(index, value) {
		    $(value).prop('disabled', true);
		});
	}
</script>