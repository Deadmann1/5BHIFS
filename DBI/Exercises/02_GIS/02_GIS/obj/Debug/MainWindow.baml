   M S B A M L   `   `   `  ����A  =02_GIS, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null!clr-namespace:_02_GIS_02_GIS  5   	       _02_GIS.MainWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   'localclr-namespace:_02_GIS     5   	   -   5          � Title$	  GIS��5   	   $	��652��6   $
��1069��6"    � 
ResizeMode$ CanResizeWithGrip=�6/    � WindowStartupLocation$ CenterScreen=�6N   $	��700��6s   $	��400��6�   .��5	      � $��0,0,-0.4,-0.2q�6    � ColumnDefinitions 5
   
   �� 5      $
��125* �6   �� 5      $
��389* �6   .+�5   
   � $��1��6   $��10,10,10,10.2q�6   ���	#FF747AB6 � RowDefinitions 5      �� 5      $
��485* �6    �� 5      $
��116* �6    .+�5      � $��0,10,0,0q�6    5      �� 5      $	��47* �6$   �� 5      $	��48* �6$    5      �� 5      $
��185* �6'   �� 5      $
��184* �6'   .+�5      ]� $��10q�6   .+�5      �� -   5         ��Name$ label1��5      $��Räumungsabschnitte��6,   $��0q�6I   $	��Top=�6T   $��18(�6i    J� RenderTransformOrigin$ 0.434,0.441&�6w   &   System.Windows.Controls.DataGrid  5      -   5          Name$! dataGridRäumungsabschnitte��5         ColumnWidth5  /System.Windows.Controls.DataGridLengthConverter$ * 6C   !	   EnableColumnVirtualization	 . 
   
IsReadOnly
 . $��0,0,0,10q�6�   $��Bottom=�6�   5      ]� 5       $��10q�6   $��1��6(   .+�5!      �� -   5        $ label2��5!      $��Mitarbeiter��6,   $��0,0,15,0q�6B   $	��Top=�6T   $��18(�6i     5"      -   5        $ dataGridMitarbeiter��5"      $ * 6<   
 . 	 . $��0,0,0,10q�6�   $��Bottom=�6�   5#      ]� 5$      $��10q�6   $��1��6(   .+�5%      �� -   5        $ label3��5%      $��Räumungsaufträge��6,   $��0,0,10,0q�6G   $	��Top=�6Y   $��18(�6n     5&      -   5        $  dataGridRäumungsaufträge��5&      $ * 6A   
 . 	 . $��5,0,5,27q�6�   $��Bottom=�6�   $ 0.5,0.5&�6�   5'      ]� 5(      $��1��6   $��10q�6,   $��1��68   .+�5)      �� -   5        $ label4��5)      $��Straßenteilstrecken��6,   $	��Top=�6J   $��18(�6_   $��0,0,10,0q�6m     5*      -	   5        $" dataGridStraßenteilstrecken��5*      $ * 6D   	 . 
 . $��0,0,0,27q�6�   $��Bottom=�6�   5+      5,      �� 5-      -
   5         ��Name$ 
lbMessages��5-      $��
10,5,10,10q�6*   $��1��6>   5.      � 5/   
   $��10,10,0,10.2q�6   ���	#FFB03232.+�50      �� $��
10,10,10,0q�6   $	��Top=�6-   .+�51      �� -   5         ��Name$ 
btnConnect��51      $��Connect��6-   $��0q�6X   $��14(�6c   �� 52      -   5        $ btnDisconnect��52      $��
Disconnect��60   $��0,10,0,0q�6E   {�.  $��14(�6�   �� 53      -   5         ��Name$ cmbConnections��53      $��0,10,0,0q�63    �� SelectedIndex$ 0��6E   $��14(�6W   �� 54      -   5        $ btnRäumungsauftrag��54      $��Räumungsauftrag��65   $��0,10,0,0q�6O   {�.  $��14(�6�   �� 55      -   5        $ btnStraßenübersicht��55      $��Straßenübersicht��66   $��0,10,0,0q�6Q   {�.  $��14(�6u   6�   �� 56      -   5        $" btnSchneeüberwachungStarten��56      $ ��Schneeüberwachung starten��6>   $	��Top=�6b   $��0,10,0,0q�6z   $��14(�6�   {�.  6�   57      �� 58      $��-5,0,10,9.8q�6   $��Bottom=�6.   $��76��6I   .+�59      �� -   5        $ label��59      $ ��Copyright @ 
Sammer Manuel��6'   $��0,30,0,0q�6P   $��Center=�6b   5:      5;      5=      5>      