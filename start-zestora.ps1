$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$maven = Join-Path $env:USERPROFILE '.maven\maven-3.9.16\bin\mvn.cmd'
$backendPom = Join-Path $root 'backend\pom.xml'

if (-not (Test-Path $maven)) { throw "Maven was not found at $maven" }
$pythonCommand = Get-Command python -ErrorAction SilentlyContinue
if (-not $pythonCommand) { throw 'Python is required to serve the frontend.' }

$backend = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
if (-not $backend) {
    Start-Process -FilePath $maven -ArgumentList '-f', $backendPom, 'spring-boot:run' -WorkingDirectory (Join-Path $root 'backend')
}

$frontend = Get-NetTCPConnection -LocalPort 5500 -State Listen -ErrorAction SilentlyContinue
if (-not $frontend) {
    $frontendPath = Join-Path $root 'frontend'
    $pythonPath = $pythonCommand.Source
    $frontendCommand = "`"$pythonPath`" -m http.server 5500 --directory `"$frontendPath`""
    Start-Process -FilePath 'cmd.exe' -ArgumentList '/k', $frontendCommand -WorkingDirectory $root
}

Start-Process 'http://localhost:5500/index.html'
Write-Host 'Zestora started.'
Write-Host 'Frontend: http://localhost:5500/index.html'
Write-Host 'Backend:  http://localhost:8080/api/health'
