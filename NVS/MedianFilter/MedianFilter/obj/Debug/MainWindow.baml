   M S B A M L   `   `   `  ����G  CMedianFilter, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null+clr-namespace:MedianFilterMedianFilter  5   	       MedianFilter.MainWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   ,localclr-namespace:MedianFilter     5   	     � Title$!  Median Filter Sammer Manuel��5   	   $	��380��6-   $	��525��6:   $	��525��6F   $	��350��6U   .��5	      � $��0,0,2,2q�6   .+�5
   
   ]� ��.  $��0,0,-6,3q�6*   .+�5      o� -   5         o�Name$
 menu��5      $��20��6!   $��Center=�6-   $	��Top=�6H   $��0,0,10,0q�6]   .@�5      m� $
��File��6   $��37��6)   $��7,0,0,0q�64   .@�5      m� -   5         m�Name$ MenuItemLoad��5      $
��Load��65   $
��Left=�6C   $	��140��6^   6j   m� 5      -   5        $ MenuItemSave��5      $
��Save��65   $
��Left=�6C   $	��140��6^   6j   m� 5      -   5        $ MenuItemExit��5      $
��Exit��65   $
��Left=�6C   $	��140��6^   6j   5      5      � 5      $��55��6   $	��Top=�6   $	��Top=�67   $��0,0,10,0q�6L   .+�5      �� -   5         ��Name$ comboBoxFilter��5      $
��Left=�63   $��	10,28,0,3q�6N   $	��120��6a   6m   �� 5      -   5         ��Name$ 	lblFilter��5      $��Filter:��6+   $	��Top=�6=   $��34��6U   $��
10,0,456,0q�6a   �� 5      -   5         ��Name$ 	btnFilter��5      $��Filter��6,   $
��Left=�6=   $��
135,28,0,3q�6X   $��75��6l   6w   � 5      -   5         �Name$ 
progessBar��5      $��24��62   $	��Top=�6>   $��215,28,10,0q�6V   �� 5      -	   5         ��Name$ txtSize��5      $
��Left=�6+   $��23��6F   $��	215,0,0,0q�6R   $	��Top=�6e   $��25��6}    �� 	MaxLength$ 2��6�   6�   �� 5      -
   5        $ lblSize��5      $��Matrix Size:��6)   $
��Left=�6@   $��	135,0,0,0q�6[   $	��Top=�6n   $��24��6�   $��70��6�   �� 5      -   5        $ lblTime��5      $�� ��6)   $
��Left=�64   $��23��6O   $��	260,0,0,0q�6[   $	��Top=�6n   $	��230��6�   5      � 5      $��0,0.4,0,-0.4q�6   	 � ColumnDefinitions	 5      �� 5      $
��250* �6'   �� 5      $
��267* �6'   .+�5       �� -   5        
 ��Name$
 imageBefore��5       $	��Top=�6-   $
��Left=�6E   �� 5!      -   5        $
 
imageAfter��5!      $��1��6,   $	��Top=�6<   $
��Left=�6T   5"      5#      5$      5%      