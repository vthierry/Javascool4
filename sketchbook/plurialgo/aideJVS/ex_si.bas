Option Explicit
' le programme principal doit se nommer main
sub main()
	Dim quantite as integer
	Dim prixUnitaire as double
	Dim prixTotal as double
	Dim remise as double
	' saisie des entr�es
	prixUnitaire = InputBox("Quel est le prix unitaire de l'article en euros ?") 
	quantite = InputBox("Quelle est la quantit� achet�e ?") 
	' calcul du prix total
	prixTotal = quantite * prixUnitaire 
	if (quantite = 1) then 
		remise = 0
	elseif (quantite <= 3) then 
		remise = 0.1 
	else
		remise = 0.2
	end if
	prixTotal = prixTotal - remise * prixTotal 
	' affichage de la sortie
	MsgBox("Le prix total vaut " & prixTotal & " euros")
end sub