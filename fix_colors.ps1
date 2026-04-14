$files = Get-ChildItem -Path "e:\Android Project\DecoNest\app\src\main" -Recurse -File
foreach ($file in $files) {
    if ($file.Extension -eq ".xml" -or $file.Extension -eq ".kt") {
        $content = Get-Content $file.FullName -Raw
        if ($content -ne $null) {
            $newContent = $content -replace "color_dark_gray", "color_text_primary" `
                                   -replace "color_gray_text", "color_text_secondary" `
                                   -replace "color_light_gold", "color_accent" `
                                   -replace "color_orange", "color_button" `
                                   -replace "color_teal", "color_primary"
            if ($content -cne $newContent) {
                [IO.File]::WriteAllText($file.FullName, $newContent)
            }
        }
    }
}
