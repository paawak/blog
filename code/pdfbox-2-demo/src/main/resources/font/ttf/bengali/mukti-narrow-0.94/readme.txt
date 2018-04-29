               ||||
             ||    ||
            ||       ||
 |||||||||||||||||||||||||
   ||   ||  ||   |||||    
    ||  ||  ||  |    || ||
    ||  ||  ||     |||||  || 
   |||  ||  ||  ||   ||
  ||||||||  ||   ||   || 
   ||   ||  ||     |||
       ||
  |||||
||    ||
  ||||  ||
          ||
   


README for MUKTI set of fonts
=======================
Mukti Narrow 
Mukti Narrow Bold
Along with MS VOLT Production Tables (muktinrw.vtp, muktinbl.vtp)

Changelog
=========
2003-11-01 Version 0.9.4
*       Removed Mukti & MuktiBold from set for copyright issues of the
 	original font (bng1-n & bng1-b)
*	Deepayan Sarkar adjusted the lookup tables so reph-raphala confusion in pango 
	is removed.
*	Updated MuktiNarrow & MuktiNarrowBold font according to Unicode 4.0
	specifications by adding avagraha and encoding khanda ta as half form
	of ta.
*	Added all half forms distinct from halant forms.
*	Added initial forms of ekaar and aikaar.
2002-11-15 Version 0.9.2 
*	Added Mukti Narrow Bold - thus completing the set.
*	Hacked out a way (using Fontlab4 Demo) to remove the bug in header
	table which prevented the font from being recognised as a valid font
	in Windows 2000 and Xp.
*	Added four new conjuncts as per list provided by Kaushik Ghose
*	Replaced ASCII glyphs with glyphs of matching style from 
	FreeFonts available at www.nongnu.org/freefonts.
*	Adjusted Positioning lookups(ukaar,uukaar,reph etc)-they are 
	almost complete.
2002-11-10 Version 0.9.0 
*	First release in Mukta Bangla Font Site 

Credits:
======
The Bangla glyphs are from GPL'ed Akruti font of Cyberscape Multimedia 
 bng2-n.ttg and available from their website <www.akruti.com/freedom>. The Latin Glyphs are from  FreeSans.ttf and FreeSansBold.ttf, part of free UCS font project<www.nongnu.org/freefont> developed by Primoz Peterlin, <primoz.peterlin@biofiz.mf.uni-lj.si>
 
In making of Open Type Tables in MS VOLT for the font (which were originaly made for the font Ani), I got help from the OT of Deepayan Sarkar's  <deepayan@stat.wisc.edu> font likhan, in addition to the materials available at Microsoft Typography site<www.microsoft.com/typography> and Unicode webpage <www.unicode.org>.

Known Bugs
=========
1. The yuktakshars are not complete, especially raphala variants of other yuktakshars, hence may cause problems by breaking of complex glyphs. However as the basic component glyphs are already in the font it will be easy to make new composites as and when remembered.
2. The font is not accepted as a valid font file in Windows Xp - even VOLT does not recognise its cmap file -probably the shareware version of Font creator program I used is the culprit.[This bug has been corrected in version 0.9.2]. I have tested the font with IE 5.5 in Windows 98 and with Yudit with acceptable results in most cases. As Yudit do not support context based Glyph substitution and Glyph positioning features, misalignment of matras may occur. Users of Gnome should be able to tell its performance in that platform of which I have no experience.

Modifying the OT tables.
===================
The OT tables are supplied as separate VOLT project files(*.vtp) to maintain portability of the fonts. To use them, open the font in VOLT, click Yes when asked "this font already contains OT tables.."etc. Then load the corrosponding project file by Import -> Import Project menu and click "Yes" when prompted "....you want to continue?" . 

Licence
=======
The files are  distributed under GNU general Public Licence version 2 dated 1991 or later as per the file Copying.

Developed by 
==========
Anirban Mitra<mitra_anirban@yahoo.co.in> for Mukta Bangla Font Project <www.nongnu.org/freebangfont> with help from other volunteers.
Web Page www.geocities.com/mitra_anirban/hobbies.htm
