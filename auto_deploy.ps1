# Auto-Deploy Watcher para Bolso-Jovem (thebest-app)
$repoPath = $PSScriptRoot
if (-not $repoPath) { $repoPath = Get-Location }

Set-Location $repoPath
Write-Host "[Auto-Deploy] Monitorando alterações na pasta: $repoPath" -ForegroundColor Cyan

while ($true) {
    try {
        $status = git status --porcelain
        if ($status) {
            Write-Host "[Auto-Deploy] Alterações detectadas. Aguardando estabilização..." -ForegroundColor Yellow
            Start-Sleep -Seconds 4
            
            git add -A
            $timestamp = (Get-Date).ToString("yyyy-MM-dd HH:mm:ss")
            $commitMessage = "Deploy automático: atualização $timestamp"
            
            Write-Host "[Auto-Deploy] Criando commit: $commitMessage" -ForegroundColor Cyan
            git commit -m $commitMessage
            
            Write-Host "[Auto-Deploy] Enviando para GitHub origin main..." -ForegroundColor Green
            git push origin main
            
            Write-Host "[Auto-Deploy] Deploy concluído com sucesso às $timestamp!`n" -ForegroundColor Green
        }
    }
    catch {
        Write-Warning "[Auto-Deploy] Erro durante deploy: $_"
    }
    Start-Sleep -Seconds 5
}
